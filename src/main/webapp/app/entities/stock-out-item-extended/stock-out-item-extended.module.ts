import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    StockOutItemDeleteDialogExtendedComponent,
    StockOutItemDeletePopupExtendedComponent,
    StockOutItemDetailExtendedComponent,
    StockOutItemExtendedComponent,
    stockOutItemExtendedRoute,
    stockOutItemPopupExtendedRoute,
    StockOutItemUpdateExtendedComponent
} from './';

const ENTITY_STATES = [...stockOutItemExtendedRoute, ...stockOutItemPopupExtendedRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        StockOutItemExtendedComponent,
        StockOutItemDetailExtendedComponent,
        StockOutItemUpdateExtendedComponent,
        StockOutItemDeleteDialogExtendedComponent,
        StockOutItemDeletePopupExtendedComponent
    ],
    entryComponents: [
        StockOutItemExtendedComponent,
        StockOutItemUpdateExtendedComponent,
        StockOutItemDeleteDialogExtendedComponent,
        StockOutItemDeletePopupExtendedComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiStockOutItemExtendedModule {}
