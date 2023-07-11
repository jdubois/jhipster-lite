export type ThemePreference = 'light-theme' | 'dark-theme';

export const getMediaPreference = (win: Window): ThemePreference => {
  if (typeof win !== 'undefined' && win.matchMedia) {
    return win.matchMedia('(prefers-color-scheme: dark)').matches ? 'dark-theme' : 'light-theme';
  }
  return 'light-theme';
};
