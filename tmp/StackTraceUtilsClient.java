package tmp;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/* Copyright (c) 2006,2014, Oracle and/or its affiliates. All rights reserved. 
 * THIS CLASS WAS ADDED TO SUPPORT JDK1.4 COMPATIBILE THIN CLIENT
 * DO NOT USE ANY JDK1.5 FEATURES IN THIS CLASS !!!!
 * DO NOT IMPORT ANY WEBLOGIC CLASSES!!!!!
 */

/**
 * @exclude
 */


public class StackTraceUtilsClient {
  private static final String WEBLOGIC_UTILS_STACK_TRACE_DISABLED = "weblogic.utils.StackTraceDisabled";
  private static final String UNKNOWN_METHOD = "unknownMethod";
  private static StackTraceElement DEFAULT_DISABLED_STACK_ELEMENT =
      new StackTraceElement(WEBLOGIC_UTILS_STACK_TRACE_DISABLED, UNKNOWN_METHOD, "", -1);
  private static final StackTraceElement[] DO_NOT_DISPLAY_STACK =
            new StackTraceElement[] {DEFAULT_DISABLED_STACK_ELEMENT};

  private static boolean isJdk9 = false;
  static {
    isJdk9 = !System.getProperty("java.version").startsWith("1.");
  }

  /**
   * Returns the stack trace for a Throwable.
   *
   * @param th               Throwable
   * @return                 String representation of its stack trace
   */
  public static String throwable2StackTrace(Throwable th) {
    if(th == null) th = new Throwable("[Null exception passed, creating stack trace for offending caller]");
    ByteArrayOutputStream ostr = new ByteArrayOutputStream();
    th.printStackTrace(new PrintStream(ostr));
    return ostr.toString();
  }

  public static String throwable2StackTraceTruncated(Throwable th, int stackTraceDepth) {
    if(th == null) th = new Throwable("[Null exception passed, creating stack trace for offending caller]");
    // -1 means print all the stackframes. use the old method in that case.
    if (stackTraceDepth == -1) 
      return throwable2StackTrace(th);
    ByteArrayOutputStream ostr = new ByteArrayOutputStream();
    PrintStream ps = new PrintStream(ostr);
    printStackTrace(th,ps,stackTraceDepth);
    return ostr.toString();
  }

  private static void printStackTrace(Throwable th, PrintStream ps, int stackTraceDepth) {
    ps.println(th);
    StackTraceElement[] trace = th.getStackTrace();
    int stackDepth = stackTraceDepth;
    if (stackDepth > trace.length)
      stackDepth = trace.length;
  
    for (int i=0; i < stackDepth; i++)
      ps.println("\tat " + trace[i]);
    if (stackDepth < trace.length)
      ps.println("\tTruncated. see log file for complete stacktrace");
    Throwable ourCause = th.getCause();
    if (ourCause != null)
      printStackTrace(ourCause,ps,stackTraceDepth);
  }

  /**
   * take the throwable passed in (typically from  a  remote
   * jvm), create a like throwable with a local stack  trace
   * and make the throwable that was  passed  in  the  cause 
   *
   * @param throwable the throwable that will become  the  cause
   *        of a new throwable  with  a  local  stack  trace
   * 
   * @return Throwable
   */
  public static Throwable getThrowableWithCause(Throwable throwable) {
    try {
      Class parameterTypes[] = { String.class, Throwable.class };
      Constructor constructor = throwable.getClass().getConstructor(parameterTypes);
      Object initargs[] = { throwable.getMessage() , throwable.getCause() };
      
      // instantiate a new throwable that will be the  cause
      // give it stack and  the  cause  from  the  throwable
      
      Throwable cause = (Throwable) constructor.newInstance(initargs);
      cause.setStackTrace(throwable.getStackTrace());
      
      setThrowableCause(throwable, cause);
    } catch (IllegalAccessException ise) {
    } catch (InstantiationException ie) {
    } catch (InvocationTargetException ite) {
    } catch (NoSuchMethodException nsme) {
    }
    
    throwable.setStackTrace(trimStackTrace((new Throwable()).getStackTrace(), 1));
    return throwable;
  }


  /**
   * take the throwable passed in (typically from  a  remote
   * jvm), create a like throwable and make the throwable that was
   * passed  in  the  cause
   *
   * @param throwable the throwable that will become  the  cause
   *        of a new throwable  no stack trace
   *
   * @return Throwable
   */
  public static Throwable getThrowableWithCauseAndNoStack(Throwable throwable) {
    try {
      Class parameterTypes[] = { String.class, Throwable.class};
      Constructor constructor = throwable.getClass().getConstructor(parameterTypes);
      Object initargs[] = { throwable.getMessage(), throwable.getCause() };

      // instantiate a new throwable that will be the  cause
      // give it cause  from  the  throwable

      Throwable newThrowable = (Throwable) constructor.newInstance(initargs);
      newThrowable.setStackTrace(DO_NOT_DISPLAY_STACK);
      setThrowableCause(throwable, newThrowable);
    } catch (IllegalAccessException ise) {
    } catch (InstantiationException ie) {
    } catch (InvocationTargetException ite) {
    } catch (NoSuchMethodException nsme) {
    }

    return throwable;
  }

  public static void scrubExceptionStackTrace(Throwable t) {
    Throwable iter = t.getCause();
    while (iter != null) {
      iter.setStackTrace(DO_NOT_DISPLAY_STACK);
      iter = iter.getCause();
    }

    t.setStackTrace(DO_NOT_DISPLAY_STACK);
  }


  /**
   * take the stack trace passed in and trim  the  specified
   * number of frames from  the  top  (not  from  the  base)
   *
   * @param stackTrace the stack trace to trim
   * @param framesToTrim the number of frames to trim
   * 
   * @return StackTraceElement[]
   */
  private static StackTraceElement[] trimStackTrace(StackTraceElement[] stackTrace, int framesToTrim) {
    if (stackTrace.length - framesToTrim <= 0) {
      return stackTrace;
    }
    
    StackTraceElement returnStackTrace[] =
      new StackTraceElement[stackTrace.length - framesToTrim];
    
    for (int i = 0; i < returnStackTrace.length; i++) {
      returnStackTrace[i] = stackTrace[i + framesToTrim];
    }
    
    return returnStackTrace;
  }

  private static void setThrowableCause(Throwable throwable, Throwable cause) throws IllegalAccessException {
    try {
      throwable.initCause(cause);
    } catch (IllegalStateException ise) {
      
      try {
        Field f = Throwable.class.getDeclaredField("cause");
        boolean set = true;
        if (!isJdk9) {
          f.setAccessible(true);
          f.set(throwable, cause);
        }
        return;
      } catch (NoSuchFieldException nsfe) {
      } catch (SecurityException se) {
      } catch (IllegalArgumentException iae) {
      }
      
      throw new IllegalAccessException("Error setting cause");
    }
  }

  /**
   * dump all messages in chained throwables.
   *
   * @param throwable to dump
   * 
   * @return dumped exception messages
   */
  public static String throwableToString(Throwable th) {
    StringBuilder sb = new StringBuilder();
    sb.append(th.toString());
    Throwable current = (Throwable)th.getCause();
    while(current != null) {
      sb.append(" caused by: ");
      sb.append(current.toString());
      current = current.getCause();
    }
    return sb.toString();
  }

  public static void main(String[] args)
  {
    try {
      throw new NullPointerException();
    } catch (Exception e) {
      System.out.println(throwableToString(e));
      System.out.println(throwable2StackTrace(e));
    }
  }
}
