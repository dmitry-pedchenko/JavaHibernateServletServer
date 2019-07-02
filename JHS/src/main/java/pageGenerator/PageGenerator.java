package pageGenerator;

import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.File;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;
import java.util.Map;

public class PageGenerator {
    private static final String HTML_DIR = "src/templates";
    private static PageGenerator pageGenerator;
    private final Configuration configutarion;

    private PageGenerator() {
        this.configutarion = new Configuration();
    }

    public static PageGenerator getInstance() {
        if (pageGenerator == null) {
            pageGenerator = new PageGenerator();
        }

        return pageGenerator;
    }

    public String getPage (String fileName, Map<String , Object> data) throws Exception {
        Writer writer = new StringWriter();

        try {
            Template template = configutarion.getTemplate(HTML_DIR + File.separator + fileName);
            template.process(data, writer);
        } catch (Exception e) {
            System.out.println(e);
        }

        return writer.toString();
    }

}