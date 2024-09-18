package com.yandrut.audio.Synth.Utils;

import com.yandrut.audio.Synth.SynthControlContainer;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import static java.lang.Math.*;


public class Utils {
    public static void invokeProcedure (Procedure procedure, boolean printStackTrace) {
        try {
            procedure.invoke();
        }
        catch (Exception e) {
            if (printStackTrace) {
                e.printStackTrace();
            }
        }
    }

    public static class WindowDesign {
        public static final Border LINE_BORDER = BorderFactory.createLineBorder(Color.black);
    }

    public static class ParameterHandling {

        public static final Robot PARAMETER_ROBOT;

        static {
            try {
                PARAMETER_ROBOT = new Robot();
            }
            catch (AWTException e) {
                throw new ExceptionInInitializerError();
            }
        }

        public static void addMouseListeners(Component component, SynthControlContainer container, int minVal,
                                             int maxVal, int valStep, ReferenceWrapper<Integer> parameter, Procedure procedure) {
            component.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    final Cursor BLANK_CURSOR = Toolkit.getDefaultToolkit().createCustomCursor(new BufferedImage(16,16,BufferedImage.TYPE_INT_ARGB),
                            new Point(0,0), "blank_cursor");
                            component.setCursor(BLANK_CURSOR);
                            container.setMouseClickLocation(e.getLocationOnScreen());
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    component.setCursor(Cursor.getDefaultCursor());
                }
            });

            component.addMouseMotionListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    super.mouseReleased(e);
                }

                @Override
                public void mouseDragged(MouseEvent e) {
                    if (container.getMouseClickLocation().y != e.getYOnScreen()) {
                        boolean mouseMovingUp = container.getMouseClickLocation().y - e.getYOnScreen() > 0;
                        if (mouseMovingUp && parameter.value < maxVal) {
                            parameter.value += valStep;
                        }
                        else if (!mouseMovingUp && parameter.value > minVal) {
                            parameter.value -= valStep;
                        }
                        if (procedure != null) {
                            invokeProcedure(procedure,true);
                        }
                        PARAMETER_ROBOT.mouseMove(container.getMouseClickLocation().x, container.getMouseClickLocation().y);
                    }
                }
            });
        }
    }

    public static class Math {
        public static double offsetTone(double baseFreq, double freqMultiplier) {
            return baseFreq * pow(2.0,freqMultiplier);
        }

        public static double frequencyToAngularFrequency (double freq) {
            return 2 * PI * freq;
        }
    }
}