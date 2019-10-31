import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    StockInProcessDeleteDialogExtendedComponent,
    StockInProcessDeletePopupExtendedComponent,
    StockInProcessDetailExtendedComponent,
    StockInProcessExtendedComponent,
    stockInProcessExtendedRoute,
    stockInProcessPopupRouteExtended,
    StockInProcessUpdateExtendedComponent
} from './';

const ENTITY_STATES = [...stockInProcessExtendedRoute, ...stockInProcessPopupRouteExtended];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        StockInProcessExtendedComponent,
        StockInProcessDetailExtendedComponent,
        StockInProcessUpdateExtendedComponent,
        StockInProcessDeleteDialogExtendedComponent,
        StockInProcessDeletePopupExtendedComponent
    ],
    entryComponents: [
        StockInProcessExtendedComponent,
        StockInProcessUpdateExtendedComponent,
        StockInProcessDeleteDialogExtendedComponent,
        StockInProcessDeletePopupExtendedComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiStockInProcessModuleExtended {}
