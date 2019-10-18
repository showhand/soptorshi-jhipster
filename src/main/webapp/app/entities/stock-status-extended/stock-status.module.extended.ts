import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    StockStatusComponentExtended,
    StockStatusDetailComponentExtended,
    StockStatusUpdateComponentExtended,
    StockStatusDeletePopupComponentExtended,
    StockStatusDeleteDialogComponentExtended,
    stockStatusRouteExtended,
    stockStatusPopupRouteExtended
} from './';

const ENTITY_STATES = [...stockStatusRouteExtended, ...stockStatusPopupRouteExtended];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        StockStatusComponentExtended,
        StockStatusDetailComponentExtended,
        StockStatusUpdateComponentExtended,
        StockStatusDeleteDialogComponentExtended,
        StockStatusDeletePopupComponentExtended
    ],
    entryComponents: [
        StockStatusComponentExtended,
        StockStatusUpdateComponentExtended,
        StockStatusDeleteDialogComponentExtended,
        StockStatusDeletePopupComponentExtended
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiStockStatusModuleExtended {}
