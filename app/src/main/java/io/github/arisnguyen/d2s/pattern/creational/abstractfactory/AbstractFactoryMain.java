package io.github.arisnguyen.d2s.pattern.creational.abstractfactory;

public class AbstractFactoryMain {
    public static void main(String[] args) {
        ThemeFactory lightFactory = new LightThemeFactory();
        ApplicationUI lightApp = new ApplicationUI(lightFactory);
        System.out.println(lightApp.renderScreen());

        ThemeFactory darkFactory = new DarkThemeFactory();
        ApplicationUI darkApp = new ApplicationUI(darkFactory);
        System.out.println(darkApp.renderScreen());
    }
}

