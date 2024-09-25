CPU=$(dpkg --print-architecture)
wget https://downloads.hydraulic.dev/conveyor/hydraulic-conveyor_15.0_"${CPU}".deb
sudo apt install ./hydraulic-conveyor_15.0_"${CPU}".deb
