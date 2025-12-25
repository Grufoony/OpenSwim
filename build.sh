#!/bin/bash

set -e

# Default values
BUILD_TYPE="debug"
TARGET_WINDOWS=false

# Parse arguments
while [[ $# -gt 0 ]]; do
    case $1 in
        -d)
            BUILD_TYPE="debug"
            shift
            ;;
        -r)
            BUILD_TYPE="release"
            shift
            ;;
        --win)
            TARGET_WINDOWS=true
            shift
            ;;
        *)
            echo "Unknown option: $1"
            echo "Usage: $0 [-d|-r] [--win]"
            echo "  -d: Build in debug mode (default)"
            echo "  -r: Build in release mode"
            echo "  --win: Build for Windows (x86_64-pc-windows-gnu)"
            exit 1
            ;;
    esac
done

# If building for Windows, check and setup cargo config
if [ "$TARGET_WINDOWS" = true ]; then
    CARGO_CONFIG="$HOME/.cargo/config.toml"
    REQUIRED_CONFIG="[target.x86_64-pc-windows-gnu]
linker = \"/usr/bin/x86_64-w64-mingw32-gcc\"
ar = \"/usr/bin/x86_64-w64-mingw32-ar\""

    # Check if config file exists and contains required configuration
    CONFIG_VALID=false
    if [ -f "$CARGO_CONFIG" ]; then
        if grep -q "\[target.x86_64-pc-windows-gnu\]" "$CARGO_CONFIG" && \
           grep -q "linker = \"/usr/bin/x86_64-w64-mingw32-gcc\"" "$CARGO_CONFIG" && \
           grep -q "ar = \"/usr/bin/x86_64-w64-mingw32-ar\"" "$CARGO_CONFIG"; then
            CONFIG_VALID=true
            echo "[OK] Cargo config for Windows cross-compilation is valid"
        fi
    fi

    # Create or update config if needed
    if [ "$CONFIG_VALID" = false ]; then
        echo "Creating/updating ~/.cargo/config.toml for Windows cross-compilation..."
        mkdir -p "$HOME/.cargo"
        echo "$REQUIRED_CONFIG" > "$CARGO_CONFIG"
        echo "[OK] Created ~/.cargo/config.toml"
    fi

    # Check if mingw-w64 is installed
    if ! command -v x86_64-w64-mingw32-gcc &> /dev/null; then
        echo "Error: MinGW-w64 toolchain not found"
        echo "Install it with: sudo apt install mingw-w64"
        exit 1
    fi

    # Check if Windows target is installed
    if ! rustup target list --installed | grep -q "x86_64-pc-windows-gnu"; then
        echo "Installing Windows target..."
        rustup target add x86_64-pc-windows-gnu
    fi

    TARGET="--target x86_64-pc-windows-gnu"
    echo "Building for Windows (x86_64-pc-windows-gnu)..."
else
    TARGET=""
    echo "Building for native platform..."
fi

# Build command
if [ "$BUILD_TYPE" = "release" ]; then
    echo "Building in release mode..."
    cargo build --release $TARGET
    
    if [ "$TARGET_WINDOWS" = true ]; then
        echo "[OK] Build complete: target/x86_64-pc-windows-gnu/release/OpenSwim.exe"
    else
        echo "[OK] Build complete: target/release/OpenSwim"
    fi
else
    echo "Building in debug mode..."
    cargo build $TARGET
    
    if [ "$TARGET_WINDOWS" = true ]; then
        echo "[OK] Build complete: target/x86_64-pc-windows-gnu/debug/OpenSwim.exe"
    else
        echo "[OK] Build complete: target/debug/OpenSwim"
    fi
fi
