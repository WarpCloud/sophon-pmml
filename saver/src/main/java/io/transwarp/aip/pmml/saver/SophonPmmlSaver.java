package io.transwarp.aip.pmml.saver;

import org.apache.spark.ml.PipelineModel;
import org.apache.spark.sql.types.StructType;
import org.dmg.pmml.PMML;
import org.jpmml.model.JAXBUtil;
import org.jpmml.sparkml.PMMLBuilder;

import javax.xml.bind.JAXBException;
import javax.xml.transform.stream.StreamResult;
import java.io.OutputStream;

public class SophonPmmlSaver implements PmmlSaver {
    @Override
    public void toPMML(StructType schema, PipelineModel m, OutputStream out) throws JAXBException {
        PMML pmml = new PMMLBuilder(schema, m)
                .build();
        JAXBUtil.marshalPMML(pmml,
                new StreamResult(out));
    }
}
