package pt.maisis.search;

public class EventMetaDataImpl implements EventMetaData {

    private String onClick;
    private String onDblClick;
    private String onMouseDown;
    private String onMouseUp;
    private String onMouseOver;
    private String onMouseMove;
    private String onMouseOut;
    private String onFocus;
    private String onBlur;
    private String onKeyPress;
    private String onKeyDown;
    private String onKeyUp;
    private String onChange;

    public EventMetaDataImpl() {
    }

    public String getOnClick() {
        return this.onClick;
    }

    public void setOnClick(final String onClick) {
        this.onClick = onClick;
    }

    public String getOnDblClick() {
        return this.onDblClick;
    }

    public void setOnDblClick(final String onDblClick) {
        this.onDblClick = onDblClick;
    }

    public String getOnMouseDown() {
        return this.onMouseDown;
    }

    public void setOnMouseDown(final String onMouseDown) {
        this.onMouseDown = onMouseDown;
    }

    public String getOnMouseUp() {
        return this.onMouseUp;
    }

    public void setOnMouseUp(final String onMouseUp) {
        this.onMouseUp = onMouseUp;
    }

    public String getOnMouseOver() {
        return this.onMouseOver;
    }

    public void setOnMouseOver(final String onMouseOver) {
        this.onMouseOver = onMouseOver;
    }

    public String getOnMouseMove() {
        return this.onMouseMove;
    }

    public void setOnMouseMove(final String onMouseMove) {
        this.onMouseMove = onMouseMove;
    }

    public String getOnMouseOut() {
        return this.onMouseOut;
    }

    public void setOnMouseOut(final String onMouseOut) {
        this.onMouseOut = onMouseOut;
    }

    public String getOnFocus() {
        return this.onFocus;
    }

    public void setOnFocus(final String onFocus) {
        this.onFocus = onFocus;
    }

    public String getOnBlur() {
        return this.onBlur;
    }

    public void setOnBlur(final String onBlur) {
        this.onBlur = onBlur;
    }

    public String getOnKeyPress() {
        return this.onKeyPress;
    }

    public void setOnKeyPress(final String onKeyPress) {
        this.onKeyPress = onKeyPress;
    }

    public String getOnKeyDown() {
        return this.onKeyDown;
    }

    public void setOnKeyDown(final String onKeyDown) {
        this.onKeyDown = onKeyDown;
    }

    public String getOnKeyUp() {
        return this.onKeyUp;
    }

    public void setOnKeyUp(final String onKeyUp) {
        this.onKeyUp = onKeyUp;
    }

    public String getOnChange() {
        return this.onChange;
    }

    public void setOnChange(final String onChange) {
        this.onChange = onChange;
    }

    public String toString() {
        return new StringBuffer("EventMetaData[").append("onClick=").append(this.onClick).append(", onDblClick=").append(this.onDblClick).append(", onMouseDown=").append(this.onMouseDown).append(", onMouseUp=").append(this.onMouseUp).append(", onMouseOver=").append(this.onMouseOver).append(", onMouseMove=").append(this.onMouseMove).append(", onMouseOut=").append(this.onMouseOut).append(", onFocus=").append(this.onFocus).append(", onBlur=").append(this.onBlur).append(", onKeyPress=").append(this.onKeyPress).append(", onKeyDown=").append(this.onKeyDown).append(", onKeyUp=").append(this.onKeyUp).append(", onChange=").append(this.onChange).append("]").toString();
    }
}
