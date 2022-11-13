import { test, expect } from '@playwright/test';

import { HomePage } from './Home-Page';

test.describe('Home', () => {
  test('display home page', async ({ page }) => {
    const homePage = new HomePage(page);
    await homePage.goto();
    await expect(page).toHaveTitle(/JHipster.*/);
  });
});
