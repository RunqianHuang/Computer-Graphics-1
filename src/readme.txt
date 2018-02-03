Groupmembers: 
Jiamin Zeng   jz863
Runqian Huang rh627

****************************************************
*Note: MeshGen.java file is in the /meshgen package*
****************************************************

Compile the file: javac meshgen/MeshGen.java

1. Generate cylinder

java meshgen/MeshGen -g cylinder (-n 32) -o cylinder.obj

2. Generate sphere

java meshgen/MeshGen -g sphere (-n 32 -m 16) -o sphere.obj

3. Compute normals

java meshgen/MeshGen -i horse-nonorms.obj -o horse.obj
java meshgen/MeshGen -i bunny-nonorms.obj -o bunny.obj

4. Extension --- Generate torus

java meshgen/MeshGen -g torus (-n 32 -m 16 -r 0.25) -o torus.obj
