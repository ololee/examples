cmake_minimum_required(VERSION 3.10.2)
project("filesearch")

add_library(filesearch
        SHARED
        filesearch.cpp
        file/SuffixTire.cpp
        file/FileInfo.cpp
        utils/SuffixUtils.cpp
        utils/FileUtils.cpp
        )

find_library(log-lib
        log )

target_link_libraries(filesearch
        ${log-lib} )