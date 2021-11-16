package com.pd.hive;

import com.pd.sec.AesUtil;
import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.PrimitiveObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.apache.log4j.Logger;

/**
 * Description:
 *
 * @author zz
 * @date 2021/11/16
 */

/**
 *
 * <pre>
 *    hadoop fs -put zodiac.jar /app/udf-lib/
 *
 *    CREATE FUNCTION default.udf_decrypt AS 'com.pd.hive.DecryptUDF' USING jar 'hdfs:/app/udf-lib/zodiac.jar';
 *    CREATE TEMPORARY FUNCTION default.udf_decrypt2 AS 'yore.DecryptUDF' USING jar 'hdfs:/app/udf-lib/zodiac.jar';
 *
 *     SELECT udf_decrypt("F9953708E0E479101887BF30ECF4606A");
 *     SELECT udf_decrypt("F9953708E0E479101887BF30ECF4606A") FROM movie LIMIT 20;
 *     DROP FUNCTION udf_decrypt;
 * </pre>
 *
 * Created by yore on 2020/5/6 09:57
 */
@Description(name="udf_encrypt",
        value="_FUNC_(value) - Returns the encrypted value of the encrypted string",
        extended = "Example:\n"
                + " > SELECT _FUNC_('test') AS a1 FROM pdata_chq.t_test_jm limit 1; \n")
public class EncryptUDF extends GenericUDF {
    private static Logger logger = Logger.getLogger(EncryptUDF.class);

    @Override
    public ObjectInspector initialize(ObjectInspector[] arguments) throws UDFArgumentException {
        if(arguments.length != 1){
            throw new UDFArgumentException("The operator 'udf_encrypt' accepts 1 arguments.");
        }
        ObjectInspector returnType = PrimitiveObjectInspectorFactory.getPrimitiveJavaObjectInspector(PrimitiveObjectInspector.PrimitiveCategory.STRING);
        return returnType;
    }

    @Override
    public Object evaluate(DeferredObject[] arguments) throws HiveException {
        if(arguments[0].get()==null)return null;
        try {
            return AesUtil.encrypt(String.valueOf(arguments[0].get()));
        }catch (Exception e){
            logger.error(e.getMessage());
            return null;
        }
    }

    @Override
    public String getDisplayString(String[] children) {
        StringBuilder sb = new StringBuilder();
        sb.append("返回 " + children[0] + " 的加密后的值")
                .append("\n")
                .append("Usage: udf_encrypt(cloumn_name)")
                .append("\n")
                .append("当加密过程中报异常，返回 null");
        return sb.toString();
    }
}
