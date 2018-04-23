package me.anisimoff.editor.Utils;

import me.anisimoff.editor.Point;

import java.io.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class Serialize {

    private static class SerializeData  implements Serializable  {
        List<Point> data;
    }

    public static List<Point> deserialize(String s ) throws SerializeException {
        try {
            byte [] data = Base64.getDecoder().decode( s );
            ObjectInputStream ois = null;
            ois = new ObjectInputStream(
                    new ByteArrayInputStream(data ) );
            Object o  = ois.readObject();
            ois.close();
            if (!(o instanceof  SerializeData)){
                throw new SerializeException();
            }

            return  ((SerializeData)o).data;
        } catch (IOException | ClassNotFoundException e) {
            throw new SerializeException();
        }
    }

    public static String serialize( List<Point> o ) throws SerializeException {
        try {
            SerializeData data = new SerializeData();
            data.data = o;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream( baos );
            oos.writeObject( data );
            oos.close();
            return Base64.getEncoder().encodeToString(baos.toByteArray());
        } catch (IOException  e) {
            throw new SerializeException();
        }
    }
}
