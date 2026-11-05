package io.github.arisnguyen.d2s.pattern.creational.prototype;

import java.util.ArrayList;
import java.util.List;

/**
 * Real-world Prototype: Pre-configured Email Template.
 * Avoids rebuilding repetitive headers, footers, and metadata from scratch.
 */
public class EmailTemplate implements Prototype<EmailTemplate> {

    private String subject;
    private String header;
    private String footer;
    private String content;
    private List<String> tags;

    public EmailTemplate(String subject, String header, String footer, String content, List<String> tags) {
        this.subject = subject;
        this.header = header;
        this.footer = footer;
        this.content = content;
        this.tags = tags != null ? new ArrayList<>(tags) : new ArrayList<>();
    }

    /**
     * Performs a deep copy to ensure modifications on the cloned template
     * do not corrupt the original prototype.
     */
    @Override
    public EmailTemplate copy() {
        return new EmailTemplate(
                this.subject,
                this.header,
                this.footer,
                this.content,
                new ArrayList<>(this.tags)
        );
    }

    public String format(String recipientName) {
        String renderedHeader = header.replace("{{name}}", recipientName);
        return renderedHeader + "\n" + content + "\n" + footer;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getFooter() {
        return footer;
    }

    public void setFooter(String footer) {
        this.footer = footer;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getTags() {
        return tags;
    }

    public void addTag(String tag) {
        this.tags.add(tag);
    }
}
