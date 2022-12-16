import { CursorType } from '@/common/primary/cursor/CursorType';

export class BodyCursorUpdater {
  constructor(private window: Window) {}

  set(cursorType: CursorType) {
    this.window.document.body.style.cursor = cursorType;
  }

  reset() {
    this.window.document.body.style.cursor = 'auto';
  }
}
