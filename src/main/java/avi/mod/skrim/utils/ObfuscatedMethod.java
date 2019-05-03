package avi.mod.skrim.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A helper class for dealing with obfuscated methods.
 */
public class ObfuscatedMethod {

  public static ObfuscatedMethod ENTITY_SET_SIZE =
      new ObfuscatedMethod("setSize", "func_70105_a").addToWhitelist(Modifier.PROTECTED).addToBlacklist(Modifier.FINAL).addParameters(float.class, float.class);

  private static final int MAX_DEPTH = 10;

  public String obName;
  public String deobName;
  public List<Integer> modifierWhitelist = new ArrayList<>();
  public List<Integer> modifierBlacklist = new ArrayList<>();
  public List<Class<?>> parameterList = new ArrayList<>();
  public Class<?>[] parameterTypes;


  public ObfuscatedMethod(String deobName, String obName) {
    this.deobName = deobName;
    this.obName = obName;
  }

  public ObfuscatedMethod addToWhitelist(Integer... modifiers) {
    this.modifierWhitelist.addAll(Arrays.asList(modifiers));
    return this;
  }

  public ObfuscatedMethod addToBlacklist(Integer... modifiers) {
    this.modifierBlacklist.addAll(Arrays.asList(modifiers));
    return this;
  }

  public ObfuscatedMethod addParameters(Class<?>... types) {
    this.parameterList.addAll(Arrays.asList(types));
    this.parameterTypes = new Class<?>[this.parameterList.size()];
    this.parameterTypes = this.parameterList.toArray(this.parameterTypes);
    return this;
  }

  public String[] getNames() {
    return new String[]{this.obName, this.deobName};
  }

  public Object invoke(Object instance, Object... args) {
    for (int depth = 0; depth < MAX_DEPTH; depth++) {
      Class c = ReflectionUtils.getSuperX(instance, depth);

      for (String methodName : this.getNames()) {
        try {
          Method method = c.getDeclaredMethod(methodName, this.parameterTypes);
          System.out.println("Found method: " + method.getName() + ", at depth: " + depth + ", className: " + c.getName());
          if (!this.matchesModifiers(method)) continue;

          System.out.println("invoking....");
          method.setAccessible(true);
          return method.invoke(instance, args);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
          e.printStackTrace();
        }
      }
    }
    return null;
  }

  private boolean matchesModifiers(Method method) {
    int modifiers = method.getModifiers();
    System.out.println(modifiers);
    System.out.println(this.modifierWhitelist);
    System.out.println(this.modifierBlacklist);
    for (int mod : this.modifierWhitelist) {
      if ((mod & modifiers) != mod) return false;
    }
    for (int mod : this.modifierBlacklist) {
      if ((mod & modifiers) == mod) return false;
    }
    System.out.println("passedTheModifier check!");
    return true;
  }

}