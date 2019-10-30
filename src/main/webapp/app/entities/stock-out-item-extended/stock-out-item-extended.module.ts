import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    StockOutItemDeleteDialogExtendedComponent,
    StockOutItemDeletePopupComponentExtended,
    StockOutItemDetailExtendedComponent,
    StockOutItemExtendedComponent,
    stockOutItemExtendedRoute,
    stockOutItemPopupRouteExtended,
    StockOutItemUpdateExtendedComponent
} from './';

const ENTITY_STATES = [...stockOutItemExtendedRoute, ...stockOutItemPopupRouteExtended];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        StockOutItemExtendedComponent,
        StockOutItemDetailExtendedComponent,
        StockOutItemUpdateExtendedComponent,
        StockOutItemDeleteDialogExtendedComponent,
        StockOutItemDeletePopupComponentExtended
    ],
    entryComponents: [
        StockOutItemExtendedComponent,
        StockOutItemUpdateExtendedComponent,
        StockOutItemDeleteDialogExtendedComponent,
        StockOutItemDeletePopupComponentExtended
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiStockOutItemModule {}
