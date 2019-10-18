import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    StockInItemComponentExtended,
    StockInItemDetailComponentExtended,
    StockInItemUpdateComponentExtended,
    StockInItemDeletePopupComponentExtended,
    StockInItemDeleteDialogComponentExtended,
    stockInItemRouteExtended,
    stockInItemPopupRouteExtended
} from './';

const ENTITY_STATES = [...stockInItemRouteExtended, ...stockInItemPopupRouteExtended];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        StockInItemComponentExtended,
        StockInItemDetailComponentExtended,
        StockInItemUpdateComponentExtended,
        StockInItemDeleteDialogComponentExtended,
        StockInItemDeletePopupComponentExtended
    ],
    entryComponents: [
        StockInItemComponentExtended,
        StockInItemUpdateComponentExtended,
        StockInItemDeleteDialogComponentExtended,
        StockInItemDeletePopupComponentExtended
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiStockInItemModuleExtended {}
