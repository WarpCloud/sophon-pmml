package io.transwarp.aip.pmml.loader;

import org.dmg.pmml.OutputField;
import org.apache.spark.ml.Transformer;
import org.jpmml.evaluator.*;
import org.jpmml.evaluator.spark.TransformerBuilder;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

public class SophonPmmlLoader implements PmmlLoader {

    @Override
    public Transformer createTransformer(InputStream inputStream) throws JAXBException, SAXException {
        Evaluator evaluator = createEvaluator(inputStream);
        TransformerBuilder builder = new TransformerBuilder(evaluator);
        Transformer transformer = builder.withLabelCol("prediction")
                .exploded(true)
                .build();
        return transformer;
    }

    @Override
    public String getSummary(InputStream inputStream) throws JAXBException, SAXException {
        Evaluator evaluator = createEvaluator(inputStream);
        return evaluator.getSummary();
    }

    @Override
    public String[] getInputFields(InputStream inputStream) throws JAXBException, SAXException {
        Evaluator evaluator = createEvaluator(inputStream);
        List<InputField> inputFields = evaluator.getInputFields();
        List<String> fields = inputFields.stream().map(inputField -> inputField.getName().getValue())
                .collect(Collectors.toList());
        return fields.toArray(new String[0]);
    }

    private Evaluator createEvaluator(InputStream in) throws JAXBException, SAXException {
        LoadingModelEvaluatorBuilder builder = new LoadingModelEvaluatorBuilder();
        builder.setOutputFilter(new OutputFilter() {
            @Override
            public boolean test(OutputField outputField) {
                return true;
            }
        });

        EvaluatorBuilder evaluatorBuilder = builder.load(in);

        Evaluator evaluator = evaluatorBuilder.build();
        evaluator.verify();

        return evaluator;
    }
}
