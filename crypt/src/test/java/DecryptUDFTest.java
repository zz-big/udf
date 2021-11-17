import com.pd.hive.DecryptUDF;
import junit.framework.Assert;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF.DeferredJavaObject;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF.DeferredObject;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.apache.hadoop.io.Text;
import org.junit.Test;

/**
 * Description:
 *
 * @author zz
 * @date 2021/11/16
 */
public class DecryptUDFTest {

    @Test
    public void decryptUDFTest() throws HiveException {
        DecryptUDF udf = new DecryptUDF();
        ObjectInspector valueOI1 = PrimitiveObjectInspectorFactory.writableVoidObjectInspector;
        ObjectInspector[] arguments = { valueOI1};
        udf.initialize(arguments);

        DeferredObject[] args = new DeferredObject[1];
        String columnVal = "F9953708E0E479101887BF30ECF4606A";
        args[0] =  new DeferredJavaObject(columnVal==null? null: new Text(columnVal));
        Object result = udf.evaluate(args);
        System.out.println(result);
        Assert.assertEquals(result, "Yore");

        columnVal = null;
        args[0] =  new DeferredJavaObject(columnVal==null? null: new Text(columnVal));
        result = udf.evaluate(args);
        Assert.assertEquals(result, null);

        columnVal = "DD64FE4645DFD4A8A5A788E94B67428F";
        args[0] =  new DeferredJavaObject(columnVal==null? null: new Text(columnVal));
        result = udf.evaluate(args);
        System.out.println(result);
        Assert.assertEquals(result, "呵护");
    }
}
