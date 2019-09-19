package cbks;
import java.io.Serializable;


 
@SuppressWarnings("serial")
public class IKVector implements Serializable {
   
  public float x;
  public float y;
  public float z;

  transient protected float[] array;


   
  public IKVector() {
  }


   
  public IKVector(float x, float y, float z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }


   
  public IKVector(float x, float y) {
    this.x = x;
    this.y = y;
  }


   
  public IKVector set(float x, float y, float z) {
    this.x = x;
    this.y = y;
    this.z = z;
    return this;
  }


   
  public IKVector set(float x, float y) {
    this.x = x;
    this.y = y;
    this.z = 0;
    return this;
  }


   
  public IKVector set(IKVector v) {
    x = v.x;
    y = v.y;
    z = v.z;
    return this;
  }


   
  public IKVector set(float[] source) {
    if (source.length >= 2) {
      x = source[0];
      y = source[1];
    }
    if (source.length >= 3) {
      z = source[2];
    } else {
      z = 0;
    }
    return this;
  }

   

   
  public IKVector copy() {
    return new IKVector(x, y, z);
  }

   
  public float[] get(float[] target) {
    if (target == null) {
      return new float[] { x, y, z };
    }
    if (target.length >= 2) {
      target[0] = x;
      target[1] = y;
    }
    if (target.length >= 3) {
      target[2] = z;
    }
    return target;
  }


   
  public float mag() {
    return (float) Math.sqrt(x*x + y*y + z*z);
  }



   
  public IKVector add(IKVector v) {
    x += v.x;
    y += v.y;
    z += v.z;
    return this;
  }


   
  public IKVector add(float x, float y) {
    this.x += x;
    this.y += y;
    return this;
  }


   
  public IKVector add(float x, float y, float z) {
    this.x += x;
    this.y += y;
    this.z += z;
    return this;
  }


   
  static public IKVector add(IKVector v1, IKVector v2) {
    return add(v1, v2, null);
  }


   
  static public IKVector add(IKVector v1, IKVector v2, IKVector target) {
    if (target == null) {
      target = new IKVector(v1.x + v2.x,v1.y + v2.y, v1.z + v2.z);
    } else {
      target.set(v1.x + v2.x, v1.y + v2.y, v1.z + v2.z);
    }
    return target;
  }


   
  public IKVector sub(IKVector v) {
    x -= v.x;
    y -= v.y;
    z -= v.z;
    return this;
  }


   
  public IKVector sub(float x, float y) {
    this.x -= x;
    this.y -= y;
    return this;
  }


   
  public IKVector sub(float x, float y, float z) {
    this.x -= x;
    this.y -= y;
    this.z -= z;
    return this;
  }


   
  static public IKVector sub(IKVector v1, IKVector v2) {
    return sub(v1, v2, null);
  }


   
  static public IKVector sub(IKVector v1, IKVector v2, IKVector target) {
    if (target == null) {
      target = new IKVector(v1.x - v2.x, v1.y - v2.y, v1.z - v2.z);
    } else {
      target.set(v1.x - v2.x, v1.y - v2.y, v1.z - v2.z);
    }
    return target;
  }


   
  public IKVector mult(float n) {
    x *= n;
    y *= n;
    z *= n;
    return this;
  }


   
  static public IKVector mult(IKVector v, float n) {
    return mult(v, n, null);
  }


   
  static public IKVector mult(IKVector v, float n, IKVector target) {
    if (target == null) {
      target = new IKVector(v.x*n, v.y*n, v.z*n);
    } else {
      target.set(v.x*n, v.y*n, v.z*n);
    }
    return target;
  }


   
  public IKVector div(float n) {
    x /= n;
    y /= n;
    z /= n;
    return this;
  }


   
  static public IKVector div(IKVector v, float n) {
    return div(v, n, null);
  }


   
  static public IKVector div(IKVector v, float n, IKVector target) {
    if (target == null) {
      target = new IKVector(v.x/n, v.y/n, v.z/n);
    } else {
      target.set(v.x/n, v.y/n, v.z/n);
    }
    return target;
  }


   
  public float dist(IKVector v) {
    float dx = x - v.x;
    float dy = y - v.y;
    float dz = z - v.z;
    return (float) Math.sqrt(dx*dx + dy*dy + dz*dz);
  }


   
  static public float dist(IKVector v1, IKVector v2) {
    float dx = v1.x - v2.x;
    float dy = v1.y - v2.y;
    float dz = v1.z - v2.z;
    return (float) Math.sqrt(dx*dx + dy*dy + dz*dz);
  }




   
  public IKVector normalize() {
    float m = mag();
    if (m != 0 && m != 1) {
      div(m);
    }
    return this;
  }


   
  public IKVector normalize(IKVector target) {
    if (target == null) {
      target = new IKVector();
    }
    float m = mag();
    if (m > 0) {
      target.set(x/m, y/m, z/m);
    } else {
      target.set(x, y, z);
    }
    return target;
  }

   
  public IKVector setMag(float len) {
    normalize();
    mult(len);
    return this;
  }


   
  public IKVector setMag(IKVector target, float len) {
    target = normalize(target);
    target.mult(len);
    return target;
  }


   
  public float heading() {
    float angle = (float) Math.atan2(y, x);
    return angle;
  }

   
  public IKVector rotate(float theta) {
    float temp = x;
    x = (float)x * (float)Math.cos(theta) - y * (float)Math.sin(theta);
    y = (float)temp * (float)Math.sin(theta) + (float)y * (float)Math.cos(theta);
    return this;
  }
  
   
  static public float angleBetween(IKVector v1, IKVector v2) {
    if (v1.x == 0 && v1.y == 0 && v1.z == 0 ) return 0.0f;
    if (v2.x == 0 && v2.y == 0 && v2.z == 0 ) return 0.0f;

    double dot = v1.x * v2.x + v1.y * v2.y + v1.z * v2.z;
    double v1mag = Math.sqrt(v1.x * v1.x + v1.y * v1.y + v1.z * v1.z);
    double v2mag = Math.sqrt(v2.x * v2.x + v2.y * v2.y + v2.z * v2.z);
    double amt = dot / (v1mag * v2mag);
    if (amt <= -1) {
      return (float)Math.PI;
    } else if (amt >= 1) {
      return 0;
    }
    return (float) Math.acos(amt);
  }


  @Override
  public String toString() {
    return "[ " + x + ", " + y + ", " + z + " ]";
  }


   
  public float[] array() {
    if (array == null) {
      array = new float[3];
    }
    array[0] = x;
    array[1] = y;
    array[2] = z;
    return array;
  }


  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof IKVector)) {
      return false;
    }
    final IKVector p = (IKVector) obj;
    return x == p.x && y == p.y && z == p.z;
  }

  
}

