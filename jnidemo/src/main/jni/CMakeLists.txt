cmake_minimum_required(VERSION 3.10.2)
project("jnidemo")

add_library(jnidemo
        SHARED
        entry.cpp)

find_library(log-lib
        log )

target_link_libraries(jnidemo
        ${log-lib} )