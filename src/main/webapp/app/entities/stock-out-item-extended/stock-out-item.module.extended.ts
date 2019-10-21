import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    StockOutItemComponentExtended,
    StockOutItemDetailComponentExtended,
    StockOutItemUpdateComponentExtended,
    StockOutItemDeletePopupComponentExtended,
    StockOutItemDeleteDialogComponentExtended,
    stockOutItemRouteExtended,
    stockOutItemPopupRouteExtended
} from './';

const ENTITY_STATES = [...stockOutItemRouteExtended, ...stockOutItemPopupRouteExtended];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        StockOutItemComponentExtended,
        StockOutItemDetailComponentExtended,
        StockOutItemUpdateComponentExtended,
        StockOutItemDeleteDialogComponentExtended,
        StockOutItemDeletePopupComponentExtended
    ],
    entryComponents: [
        StockOutItemComponentExtended,
        StockOutItemUpdateComponentExtended,
        StockOutItemDeleteDialogComponentExtended,
        StockOutItemDeletePopupComponentExtended
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiStockOutItemModule {}
