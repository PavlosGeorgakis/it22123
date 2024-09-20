public class RoleDto {
    private Integer id;

    private RoleDto(Builder builder) {
        this.id = builder.id;
    }

    public Integer getId() {
        return id;
    }

    public static class Builder {
        private Integer id;

        public Builder setId(Integer id) {
            this.id = id;
            return this;
        }

        public RoleDto build() {
            return new RoleDto(this);
        }
    }
}

