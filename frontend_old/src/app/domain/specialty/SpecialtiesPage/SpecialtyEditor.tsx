import * as React from "react";

export type Message = {
  kind: "OK" | "ERROR";
  message: string;
};

type SpecialtyEditorProps = {
  message?: Message | null;
  initialSpecialty: string;
  onUpdateSpecialty: (newSpecialty: string) => void;
  onRemoveSpecialty?: () => void;
  onChange?: () => void;
  onBlur?: () => void;
};

type SpecialtyEditorState = {
  name: string;
};

export default class SpecialtyEditor extends React.Component<SpecialtyEditorProps, SpecialtyEditorState> {
  inputNode?: HTMLInputElement;

  constructor(props: SpecialtyEditorProps) {
    super(props);
    this.state = {
      name: props.initialSpecialty
    };

    this.onUpdateClicked = this.onUpdateClicked.bind(this);
    this.onResetClicked = this.onResetClicked.bind(this);
  }

  onUpdateClicked() {
    const { onUpdateSpecialty } = this.props;
    const { name } = this.state;
    onUpdateSpecialty(name);
  }

  onResetClicked() {
    const { initialSpecialty } = this.props;
    const { inputNode } = this;
    this.setState({ name: initialSpecialty });
    inputNode && inputNode.focus();
  }

  render() {
    const { name } = this.state;
    const { onUpdateSpecialty, onRemoveSpecialty, initialSpecialty, message, onBlur, onChange } = this.props;

    const rowStyle = {
      marginBottom: "1rem"
    };

    const width = (w: string) => ({
      width: w,
      maxWidth: w,
      minWidth: w
    });

    return (
      <tr>
        <td style={width("70%")}>
          <input
            className="form-control"
            ref={n => (this.inputNode = n)}
            value={name}
            onChange={e => {
              this.setState({ name: e.currentTarget.value });
              onChange && onChange();
            }}
            onBlur={onBlur}
          />
          {message &&
            <b>
              {message.kind}: {message.message}
            </b>}
        </td>
        <td style={width("30%")}>
          <button className="btn btn-default" type="submit" onClick={this.onUpdateClicked}>
            {initialSpecialty ? "Update" : "Add"}
          </button>
          {onRemoveSpecialty &&
            <button className="btn btn-default" type="submit" onClick={onRemoveSpecialty}>
              Remove
            </button>}
          <button className="btn btn-default" type="submit" onClick={this.onResetClicked}>
            Reset
          </button>
        </td>
      </tr>
    );
  }
}
