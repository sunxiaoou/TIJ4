//: interfaces/HorrorShow.java
package interfaces; /* Added by Eclipse.py */
// Extending an interface with inheritance.

// exercise 10.14: implement DangerousMonster and Vampire with anonymous inter class

/*
interface Monster {
  void menace();
}

interface DangerousMonster extends Monster {
  void destroy();
}

interface Lethal {
  void kill();
}

class DragonZilla implements DangerousMonster {
  public void menace() {}
  public void destroy() {}
}

interface Vampire extends DangerousMonster, Lethal {
  void drinkBlood();
}

class VeryBadVampire implements Vampire {
  public void menace() {}
  public void destroy() {}
  public void kill() {}
  public void drinkBlood() {}
}
*/

public class HorrorShow2 {
  static public DangerousMonster dangerousMonster() {
    return new DangerousMonster() {
      @Override
      public void menace() {
        System.out.println("menace()");
      }
      public void destroy() {
        System.out.println("destroy()");
      }
    };
  }

  static public Vampire vampire() {
    return new Vampire() {
      @Override
      public void drinkBlood() {
        System.out.println("vampire drinkBlood()");
      }

      @Override
      public void destroy() {
        System.out.println("vampire destroy()");
      }

      @Override
      public void kill() {
        System.out.println("vampire kill()");
      }

      @Override
      public void menace() {
        System.out.println("vampire menace()");
      }
    };
  }

  static void u(Monster b) { b.menace(); }
  static void v(DangerousMonster d) {
    d.menace();
    d.destroy();
  }
  static void w(Lethal l) { l.kill(); }
  public static void main(String[] args) {
    DangerousMonster barney = dangerousMonster();
    u(barney);
    v(barney);
    Vampire vlad = vampire();
    u(vlad);
    v(vlad);
    w(vlad);
  }
} ///:~
