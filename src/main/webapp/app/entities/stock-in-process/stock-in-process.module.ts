import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    StockInProcessComponent,
    StockInProcessDetailComponent,
    StockInProcessUpdateComponent,
    StockInProcessDeletePopupComponent,
    StockInProcessDeleteDialogComponent,
    stockInProcessRoute,
    stockInProcessPopupRoute
} from './';

const ENTITY_STATES = [...stockInProcessRoute, ...stockInProcessPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        StockInProcessComponent,
        StockInProcessDetailComponent,
        StockInProcessUpdateComponent,
        StockInProcessDeleteDialogComponent,
        StockInProcessDeletePopupComponent
    ],
    entryComponents: [
        StockInProcessComponent,
        StockInProcessUpdateComponent,
        StockInProcessDeleteDialogComponent,
        StockInProcessDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiStockInProcessModule {}
