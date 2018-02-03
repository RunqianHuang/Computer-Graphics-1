package meshgen;

import java.util.ArrayList;
import java.util.TreeSet;
import java.util.Iterator;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import math.Vector2;
import math.Vector3;
import java.lang.Math;
import java.util.Scanner;



public class MeshGen {

  public static void main(String[] args) {
    OBJMesh cylinder_test = cylinder(32);
    try{
      // OBJMesh right_cylinder = new OBJMesh("../data/cylinder-reference.obj");
      // OBJMesh.compare(cylinder_test, right_cylinder, true, (float)0.00001);
      OBJMesh add_normal = compute_graphic("../data/horse-nonorms.obj");
      OBJMesh right_bunny = new OBJMesh("../data/horse-norms-reference.obj");
      add_normal.writeOBJ("test.obj");
      OBJMesh.compare(add_normal, right_bunny, true, (float)0.0001);
    }catch(Exception e){}
  }

  public static OBJMesh cylinder(int n){
    int m = n;
    OBJMesh cylinder = new OBJMesh();
    Vector3 top = new Vector3(0, 1, 0);
    Vector2 top_uv = new Vector2((float)0.75, (float)0.75); //uv for the centre point of upper
    Vector3 top_normal = new Vector3(0, 1, 0);
    cylinder.positions.add(top);
    cylinder.uvs.add(top_uv);
    cylinder.normals.add(top_normal);

    int index = 0;
    while (index < m) {
      Vector3 next = new Vector3(1);
      Vector2 next_uv1 = new Vector2((float)0.75);    //uv for the upper
      Vector2 next_uv2 = new Vector2(0, (float)0.5);  //uv for the side
      Vector3 next_normal = new Vector3(0); // normals for the upper and buttom
      next.x = -(float)Math.sin((Math.PI*2/m)*index);
      next.z = -(float)Math.cos((Math.PI*2/m)*index);
      next_normal.x = -(float)Math.sin((Math.PI*2/m)*index);
      next_normal.z = -(float)Math.cos((Math.PI*2/m)*index);
      cylinder.positions.add(next);
      next_uv1.add(-(float)(0.25*Math.sin((Math.PI*2/m)*index)), (float)(0.25*Math.cos((Math.PI*2/m)*index)));
      next_uv2.add(((float)1/m)*index,0);
      cylinder.uvs.add(next_uv1);
      cylinder.uvs.add(next_uv2);
      cylinder.normals.add(next_normal);
      index++;
    }
    Vector3 buttom = new Vector3(0, -1, 0);
    Vector2 buttoom_vu = new Vector2((float)0.25,(float)0.75);
    Vector3 buttom_normal = new Vector3(0, -1, 0);
    cylinder.positions.add(buttom);
    cylinder.uvs.add(buttoom_vu);
    cylinder.normals.add(buttom_normal);

    index = 0;
    while (index < m) {
      Vector3 next = new Vector3(-1);
      Vector2 next_uv1 = new Vector2((float)0.25, (float)0.75);  //uv for the buttom
      Vector2 next_uv2 = new Vector2(0,0); //uv for the side
      next.x = -(float)Math.sin((Math.PI*2/m)*index);
      next.z = -(float)Math.cos((Math.PI*2/m)*index);
      cylinder.positions.add(next);
      next_uv1.add(-(float)(0.25*Math.sin((Math.PI*2/m)*index)), -(float)(0.25*Math.cos((Math.PI*2/m)*index)));
      next_uv2.add(((float)1/m)*index,0);
      cylinder.uvs.add(next_uv1);
      cylinder.uvs.add(next_uv2);
      index++;
    }
    cylinder.uvs.add(new Vector2(1,(float)0.5));
    cylinder.uvs.add(new Vector2(1,0));

    index = 0;
    while (index < m) {
      OBJFace face_upper = new OBJFace(3, true, true);
      face_upper.setVertex(0, 0, 0, 0);
      face_upper.setVertex(1, index+1, 2*index+1, 0);
      face_upper.setVertex(2, index+2, 2*(index+1)+1, 0);
      if (index == m-1) face_upper.setVertex(2, 1, 1, 0);
      cylinder.faces.add(face_upper);
      OBJFace side_1 = new OBJFace(3, true, true);
      side_1.setVertex(0, index+1, 2*index+2, index+1);
      side_1.setVertex(1, index+2+m, 2*index+2*m+3, index+1);
      //side_1.setVertex(2, index+3+m, 2*(index+1)+2*m+3, index+2);
      side_1.setVertex(2, index+2, 2*(index+1)+2, index+2);
      //if (index == m-1) side_1.setVertex(2, 2, cylinder.uvs.size()-1, 1);
      if (index == m-1) side_1.setVertex(2, 1, cylinder.uvs.size()-2, 1);
      cylinder.faces.add(side_1);
      OBJFace side_2 = new OBJFace(3, true, true);
      side_2.setVertex(0, index+3+m, 2*(index+1)+2*m+3, index+2);
      side_2.setVertex(1, index+2, 2*(index+1)+2, index+2);
      //side_2.setVertex(2, index+1, 2*index+2, index+1);
      side_2.setVertex(2, index+2+m, 2*index+2*m+3, index+1);
      // if (index == m-1) {
      //   side_2.setVertex(1, 1, cylinder.uvs.size()-2, 1);
      //   side_2.setVertex(0,m+2, cylinder.uvs.size()-1, 2);
      // }
      if (index == m-1) {
        side_2.setVertex(1, 1, cylinder.uvs.size()-2, 1);
        side_2.setVertex(0, 2+m, cylinder.uvs.size()-1, 1);
      }
      cylinder.faces.add(side_2);
      OBJFace face_buttom = new OBJFace(3, true, true);
      face_buttom.setVertex(0, m+1, 2*m+1, m+1);
      face_buttom.setVertex(2, index+2+m, 2*(index+m)+2, m+1);
      face_buttom.setVertex(1, index+3+m, 2*(index+1+m)+2, m+1);
      if (index == m-1) face_buttom.setVertex(1, m+2, 2+2*m, m+1);
      cylinder.faces.add(face_buttom);
      index++;
    }
    cylinder.isValid(true);
    try {
      cylinder.writeOBJ("cylinder_v1.obj");
    }
    catch (Exception e) {}
    return cylinder;
  }

  public static OBJMesh compute_graphic (String input_name) {
    try{
      OBJMesh origin = new OBJMesh(input_name);
      for(int index = 0; index < origin.positions.size(); index++) {
        origin.normals.add(new Vector3(0,0,0));
      }
      for (OBJFace face: origin.faces) {
        Vector3 normal_toAdd = Compute_normal(face, origin);
        int count = 0;
        for (int index: face.positions) {
          if(count == 0) face.normals = new int[3];
          face.normals[count] = index;
          count++;
          origin.normals.get(index).add(normal_toAdd);
        }
      }

      for (Vector3 nor: origin.normals) {
          nor.normalize();
      }
      return origin;
    }
    catch(Exception e){return null;}
  }

  private static Vector3 Compute_normal (OBJFace face, OBJMesh origin) {
    Vector3 point_0 = origin.positions.get(face.positions[0]).clone();
    Vector3 point_1 = origin.positions.get(face.positions[1]).clone();
    Vector3 point_2 = origin.positions.get(face.positions[2]).clone();
    Vector3 v_01 = point_1.sub(point_0).clone();
    Vector3 v_02 = point_2.sub(point_0).clone();
    Vector3 nor = new Vector3(((v_01.y)*(v_02.z)-(v_01.z)*(v_02.y)),((v_01.z)*(v_02.x)-(v_01.x)*(v_02.z)),
    ((v_01.x)*(v_02.y)-(v_02.x)*(v_01.y)));
    return nor.normalize();
  }
}
