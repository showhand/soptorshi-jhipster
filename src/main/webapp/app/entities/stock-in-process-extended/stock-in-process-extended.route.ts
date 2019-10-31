import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IStockInProcess, StockInProcess } from 'app/shared/model/stock-in-process.model';
import { StockInProcessExtendedService } from 'app/entities/stock-in-process-extended/stock-in-process-extended.service';
import { StockInProcessExtendedComponent } from 'app/entities/stock-in-process-extended/stock-in-process-extended.component';
import { StockInProcessDetailExtendedComponent } from 'app/entities/stock-in-process-extended/stock-in-process-detail-extended.component';
import { StockInProcessUpdateExtendedComponent } from 'app/entities/stock-in-process-extended/stock-in-process-update-extended.component';
import { StockInProcessDeletePopupExtendedComponent } from 'app/entities/stock-in-process-extended/stock-in-process-delete-dialog-extended.component';

@Injectable({ providedIn: 'root' })
export class StockInProcessExtendedResolve implements Resolve<IStockInProcess> {
    constructor(private service: StockInProcessExtendedService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IStockInProcess> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<StockInProcess>) => response.ok),
                map((stockInProcess: HttpResponse<StockInProcess>) => stockInProcess.body)
            );
        }
        return of(new StockInProcess());
    }
}

export const stockInProcessExtendedRoute: Routes = [
    {
        path: '',
        component: StockInProcessExtendedComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'StockInProcesses'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: StockInProcessDetailExtendedComponent,
        resolve: {
            stockInProcess: StockInProcessExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'StockInProcesses'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: StockInProcessUpdateExtendedComponent,
        resolve: {
            stockInProcess: StockInProcessExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'StockInProcesses'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: StockInProcessUpdateExtendedComponent,
        resolve: {
            stockInProcess: StockInProcessExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'StockInProcesses'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const stockInProcessPopupRouteExtended: Routes = [
    {
        path: ':id/delete',
        component: StockInProcessDeletePopupExtendedComponent,
        resolve: {
            stockInProcess: StockInProcessExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'StockInProcesses'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
