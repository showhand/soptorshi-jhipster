import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    StockOutItemComponent,
    StockOutItemDetailComponent,
    StockOutItemUpdateComponent,
    StockOutItemDeletePopupComponent,
    StockOutItemDeleteDialogComponent,
    stockOutItemRoute,
    stockOutItemPopupRoute
} from './';

const ENTITY_STATES = [...stockOutItemRoute, ...stockOutItemPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        StockOutItemComponent,
        StockOutItemDetailComponent,
        StockOutItemUpdateComponent,
        StockOutItemDeleteDialogComponent,
        StockOutItemDeletePopupComponent
    ],
    entryComponents: [
        StockOutItemComponent,
        StockOutItemUpdateComponent,
        StockOutItemDeleteDialogComponent,
        StockOutItemDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiStockOutItemModule {}
