cmake_minimum_required(VERSION 3.10.2)

set(${CMAKE_C_FLAGS}, "${CMAKE_C_FLAGS}")


set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=gnu++11 -Wall -Werror")


project("jnidemo")

add_library(jnidemo
        SHARED
        samples/texturemap.cpp
        gles/traingles.cpp
        gles/glutils.cpp
        entry.cpp
        mmaputils.cpp)

find_library(log-lib
        log)


target_link_libraries(jnidemo
        android
        GLESv3
        EGL
        GLESv1_CM
        -ljnigraphics
        ${log-lib})