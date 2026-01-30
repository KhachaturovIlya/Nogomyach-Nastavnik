package presenter.impl.widget;

import presenter.ILangService;
import presenter.impl.interfaces.ILayoutStrategy;
import shared.Color;
import shared.Shape;
import shared.Vector2;

import java.util.ArrayList;
import java.util.List;

public class DynamicContainer extends Container {
    private final ILayoutStrategy layoutStrategy;
    private final Widget template;
    private final DataBinding templatesInfo;

    DynamicContainer(boolean active, String name, Shape shape, Color shapeColor,
                     TextConfig textConfig, Vector2 normalizedPosition, ILayoutStrategy layoutStrategy,
                     Widget template, DataBinding templatesInfo) {

        super(active, name, shape, shapeColor, textConfig, normalizedPosition, new ArrayList<>());
        this.layoutStrategy = layoutStrategy;
        this.template = template;
        this.templatesInfo = templatesInfo;
    }

    public DataBinding getTemplatesInfo() {
        return templatesInfo;
    }

    public void rebuild(List<String> templatesInfo) {
        children.clear();
        List<Widget> generated = generate(templatesInfo);

        layoutStrategy.apply(generated);

        for (Widget widget : generated) {
            children.put(widget.getId(), widget);
        }
    }

    private List<Widget> generate(List<String> templatesInfo) {
        List<Widget> generated = new ArrayList<>();

        TextConfig templateTextConfig = this.template.getTextConfig();

        for (String templateInfo : templatesInfo) {
            List<DataBinding> dataBindings = new ArrayList<>();

            for (DataBinding dataBinding : templateTextConfig.bindings()) {
                dataBindings.add(
                    new DataBinding(
                        dataBinding.query(),
                        templateInfo
                    )
                );
            }

            generated.add(template.wither(
              new TextConfig(
                  templateTextConfig.id(),
                  templateTextConfig.color(),
                  templateTextConfig.type(),
                  dataBindings
              )
            ));
        }

        return generated;
    }
}
