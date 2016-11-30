package com.jiyuanime.listener;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.event.EditorFactoryEvent;
import com.intellij.openapi.editor.event.EditorFactoryListener;
import com.jiyuanime.config.Config;
import com.jiyuanime.particle.ParticlePanel;
import com.jiyuanime.shake.ShakeManager;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * 编辑界面获焦监听
 * <p>
 * Created by suika on 15-12-13.
 */
public class ActivatePowerEditorFocusListener implements EditorFactoryListener {

    @Override
    public void editorCreated(@NotNull EditorFactoryEvent editorFactoryEvent) {
        Editor editor = editorFactoryEvent.getEditor();
        JComponent jContentComponent = editor.getContentComponent();
        JComponent jComponent = editor.getComponent();

        Config.State state = Config.getInstance().state;

        if (state.IS_SHAKE)
            ShakeManager.getInstance().init(jComponent);

        if (state.IS_SPARK) {
            ParticlePanel particlePanel = ParticlePanel.getInstance();
            if (!particlePanel.isEnable()) {
                particlePanel.init(jContentComponent);
                particlePanel.setEnableAction(true);
                jContentComponent.setBorder(ParticlePanel.getInstance());
            } else {
                if (ParticlePanel.getInstance().getNowEditorJComponent() != jContentComponent) {
                    ParticlePanel.getInstance().reset(jContentComponent);
                    jContentComponent.setBorder(ParticlePanel.getInstance());
                }
            }

        }
    }

    @Override
    public void editorReleased(@NotNull EditorFactoryEvent editorFactoryEvent) {
        ShakeManager.getInstance().clear();
        ParticlePanel.getInstance().clear();
    }
}