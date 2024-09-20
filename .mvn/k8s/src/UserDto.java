public class UserDto {
    private Long id;

    private UserDto(Builder builder) {
        this.id = builder.id;
    }

    public Long getId() {
        return id;
    }

    public static class Builder {
        private Long id;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public UserDto build() {
            return new UserDto(this);
        }
    }
}

