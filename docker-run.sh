#!/usr/bin/sh
docker run -it -p 4000:4000 -v $HOME/Dropbox/taroyabuki.github.io:/srv/jekyll --name jekyll jekyll/jekyll bash
