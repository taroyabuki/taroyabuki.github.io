#!/usr/bin/sh
docker run --rm -it -p 4000:4000 -v $HOME/Dropbox/taroyabuki.github.io:/www jekyll/jekyll bash
