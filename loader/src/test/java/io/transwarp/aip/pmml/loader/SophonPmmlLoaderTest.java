package io.transwarp.aip.pmml.loader;

import org.apache.spark.ml.Transformer;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;

import java.io.InputStream;

public class SophonPmmlLoaderTest {
    @Test
    public void testPmmlLoad() throws JAXBException, SAXException {
        InputStream input = this.getClass().getResourceAsStream("/LR_test.pmml");
        Transformer transformer = new SophonPmmlLoader().createTransformer(input);
    }
}