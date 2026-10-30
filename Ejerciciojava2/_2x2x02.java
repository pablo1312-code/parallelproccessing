package Ejerciciojava2;
import java.io.File;

public class _2x2x02
{
    public static void metodo(String[] args)
    {
        File dir = new File(args[0]);
        String ext = args[1].startsWith(".") ? args[1] : "." + args[1];

        String[] ficheros = dir.list();   // null si no es directorio o no se puede leer

        if( !dir.isDirectory() || ficheros == null )
        {
            System.out.println("'" + args[0] + "' no es un directorio válido.");
            return;
        }

        for( String f : ficheros )
            if( f.toLowerCase().endsWith(ext.toLowerCase()) )
                System.out.println(f);
    }

    public static void main(String[] args)
    {
        if( args.length < 2 )
        {
            System.out.println("Uso: java _2x2x02 <directorio> <extensión>");
            return;
        }
        metodo(args);
    }
}