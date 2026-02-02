package presenter.impl.widget;

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

    DynamicContainer(boolean active, String name, Shape shape, Color shapeColor, List<String> img,
                     TextConfig textConfig, Vector2 normalizedPosition, ILayoutStrategy layoutStrategy,
                     Widget template, DataBinding templatesInfo) {

        super(active, name, shape, shapeColor, img, textConfig, normalizedPosition, new ArrayList<>());
        this.layoutStrategy = layoutStrategy;
        this.template = template;
        this.templatesInfo = templatesInfo;
    }

    public DynamicContainer(DynamicContainer container) {
        super(container);
        this.layoutStrategy = container.layoutStrategy;
        this.template = container.template;
        this.templatesInfo = container.templatesInfo;
    }

    @Override
    public DynamicContainer wither(TextConfig textConfig) {
        DynamicContainer container = new DynamicContainer(this);

        container.textConfig = textConfig;

        return container;
    }

    @Override
    public DynamicContainer clone() {
        return new DynamicContainer(this);
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

    static private void injectClickActions(Widget widget, String id) {
        if (widget instanceof Button button) {

            List<DataBinding> clickActions = new ArrayList<>();

            for (DataBinding binding : button.getClickActions()) {
                String setId = binding.subjectId().isEmpty() ? id : binding.subjectId();

                clickActions.add(new DataBinding(
                        binding.query(),
                        setId
                ));
            }

            button.setClickActions(clickActions);
        }
    }

    static private void injectSubjectIdsInChildren(Widget widget, String id) {
        if (widget instanceof Container container) {
            for (Widget children : container.children.values()) {
                injectSubjectId(children, id);
            }
        }
    }

    static private void injectSubjectId(Widget widget, String id) {
        TextConfig templateTextConfig = widget.getTextConfig();

        List<DataBinding> dataBindings = new ArrayList<>();

        for (DataBinding binding : templateTextConfig.bindings()) {
            String setId = binding.subjectId().isEmpty() ? id : binding.subjectId();

            dataBindings.add(new DataBinding(
                    binding.query(),
                    setId
            ));
        }

        widget.setTextConfig(new TextConfig(
            templateTextConfig.id(),
            templateTextConfig.color(),
            templateTextConfig.type(),
            dataBindings
        ));

        injectClickActions(widget, id);
        injectSubjectIdsInChildren(widget, id);
    }

    private List<Widget> generate(List<String> templatesInfo) {
        List<Widget> generated = new ArrayList<>();

        for (String templateInfo : templatesInfo) {
            Widget generatedWidget = template.clone();

            injectSubjectId(generatedWidget, templateInfo);

            generated.add(generatedWidget);
        }

        return generated;
    }
}
