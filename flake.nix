{
  description = "StudyPlanner development environment";

  inputs = {
    # Pinned so the toolchain belongs to this repository rather than to
    # whatever nixpkgs happens to be installed on the machine running it.
    # `nix flake update` moves this deliberately; the lockfile is what
    # everyone else builds.
    nixpkgs.url = "github:NixOS/nixpkgs/1d060086d60cb76a40cf37109f979d88a5bd6396";

    # The base JVM shell from ~/dotfiles/devshells. `follows` makes it run
    # against the pin above, so nixpkgs is still chosen here, not by the
    # shared flake. It supplies the JDK, Gradle and Maven; this file only
    # adds what is specific to Compose Desktop.
    devshells = {
      url = "path:/home/mark/dotfiles/devshells";
      inputs.nixpkgs.follows = "nixpkgs";
    };
  };

  outputs =
    { devshells, ... }:
    let
      # This machine and the desktop target are Linux; the Windows build is
      # gradlew.bat with a JDK, not a Nix shell.
      system = "x86_64-linux";
      lib = devshells.lib.${system};

      # Compose Desktop renders through Skiko, which dlopen()s its X11/GTK/audio
      # natives at runtime. NixOS has no /usr/lib, so these have to be on
      # LD_LIBRARY_PATH or the process dies before the first window appears.
      runtimeLibs = with lib.pkgs; [
        libxkbcommon
        fontconfig
        freetype
        glib
        gtk3
        pango
        cairo
        gdk-pixbuf
        harfbuzz
        alsa-lib
        zlib
        dbus
        libx11
        libxext
        libxrender
        libxtst
        libxi
        libxrandr
        libxfixes
        libxcursor
        libxcb
        libxxf86vm
        libxcomposite
        libxdamage
      ];

      # jdk25 is mkJava's default, which is what desktopApp/build.gradle.kts
      # targets.
      base = lib.mkJava {
        extraPackages = runtimeLibs;
      };
    in
    {
      devShells.${system}.default = base.overrideAttrs (_: {
        # dlopen() ignores the JDK's RPATH, so the natives must be findable by
        # bare soname.
        #
        # The Mesa/GL userspace is the one thing that comes from the machine
        # rather than from this shell: those userspace drivers have to match
        # the running kernel module, and NixOS already publishes the matching
        # set in /run/opengl-driver/lib. Duplicating mesa here would only risk
        # a mismatch.
        LD_LIBRARY_PATH = lib.pkgs.lib.makeLibraryPath runtimeLibs
          + ":/run/opengl-driver/lib";
      });
    };
}
