package xyz.leyuna.laboratory.Strategy;

public enum ServiceEnum {
    SERVICE1(1, "业务描述", "service1"),
    SERVICE2(2, "业务描述", "service2"),
    SERVICE3(3, "业务描述", "service3"),
    SERVICE4(4, "业务描述", "service4");
    /**
     * 业务type
     */
    private Integer type;

    /**
     * 业务描述
     */
    private String name;

    /**
     * 对应处理器
     */
    private String processor;

    ServiceEnum(int i, String name, String processor) {
        this.type = i;
        this.name = name;
        this.processor = processor;
    }


    public static ServiceEnum valueOf(Integer type) {
        if (type == null) {
            return null;
        }
        for (ServiceEnum serviceEnum : ServiceEnum.values()) {
            if (type.equals(serviceEnum.getType())) {
                return serviceEnum;
            }
        }
        return null;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProcessor() {
        return processor;
    }

    public void setProcessor(String processor) {
        this.processor = processor;
    }
}
