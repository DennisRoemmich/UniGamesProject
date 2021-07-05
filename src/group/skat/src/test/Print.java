package test;

public class Print {


    private static final String ACTIVE = "WARNING;ERROR;CONSOLE";

    public static void debug(String key, Object obj){

        if ( ACTIVE.contains(key) ){

                print("§" + key + " : " + obj + "\n");

        }

    }

    public static void console(Object obj){

        var key = "CONSOLE";

        if ( ACTIVE.contains(key) ){

            print(obj);

        }

    }

    public static void times(int times, Object obj){

        for(var i = 0; i < times; i++){
            print(obj);
        }

    }

    private static void print(Object obj){

        System.out.print(obj);

    }


}
