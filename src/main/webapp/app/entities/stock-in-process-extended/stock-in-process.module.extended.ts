import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    StockInProcessComponentExtended,
    StockInProcessDetailComponentExtended,
    StockInProcessUpdateComponentExtended,
    StockInProcessDeletePopupComponentExtended,
    StockInProcessDeleteDialogComponentExtended,
    stockInProcessRouteExtended,
    stockInProcessPopupRouteExtended
} from './';

const ENTITY_STATES = [...stockInProcessRouteExtended, ...stockInProcessPopupRouteExtended];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        StockInProcessComponentExtended,
        StockInProcessDetailComponentExtended,
        StockInProcessUpdateComponentExtended,
        StockInProcessDeleteDialogComponentExtended,
        StockInProcessDeletePopupComponentExtended
    ],
    entryComponents: [
        StockInProcessComponentExtended,
        StockInProcessUpdateComponentExtended,
        StockInProcessDeleteDialogComponentExtended,
        StockInProcessDeletePopupComponentExtended
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiStockInProcessModuleExtended {}
