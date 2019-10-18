import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    StockOutItemComponentExtended,
    StockOutItemDetailComponentExtended,
    StockOutItemUpdateComponentExtended,
    StockOutItemDeletePopupComponent,
    StockOutItemDeleteDialogComponentExtended,
    stockOutItemRouteExtended,
    stockOutItemPopupRoute
} from './';

const ENTITY_STATES = [...stockOutItemRouteExtended, ...stockOutItemPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        StockOutItemComponentExtended,
        StockOutItemDetailComponentExtended,
        StockOutItemUpdateComponentExtended,
        StockOutItemDeleteDialogComponentExtended,
        StockOutItemDeletePopupComponent
    ],
    entryComponents: [
        StockOutItemComponentExtended,
        StockOutItemUpdateComponentExtended,
        StockOutItemDeleteDialogComponentExtended,
        StockOutItemDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiStockOutItemModule {}
