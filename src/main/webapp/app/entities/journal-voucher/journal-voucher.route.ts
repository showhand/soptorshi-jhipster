import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JournalVoucher } from 'app/shared/model/journal-voucher.model';
import { JournalVoucherService } from './journal-voucher.service';
import { JournalVoucherComponent } from './journal-voucher.component';
import { JournalVoucherDetailComponent } from './journal-voucher-detail.component';
import { JournalVoucherUpdateComponent } from './journal-voucher-update.component';
import { JournalVoucherDeletePopupComponent } from './journal-voucher-delete-dialog.component';
import { IJournalVoucher } from 'app/shared/model/journal-voucher.model';

@Injectable({ providedIn: 'root' })
export class JournalVoucherResolve implements Resolve<IJournalVoucher> {
    constructor(private service: JournalVoucherService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IJournalVoucher> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<JournalVoucher>) => response.ok),
                map((journalVoucher: HttpResponse<JournalVoucher>) => journalVoucher.body)
            );
        }
        return of(new JournalVoucher());
    }
}

export const journalVoucherRoute: Routes = [
    {
        path: '',
        component: JournalVoucherComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'JournalVouchers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: JournalVoucherDetailComponent,
        resolve: {
            journalVoucher: JournalVoucherResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'JournalVouchers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: JournalVoucherUpdateComponent,
        resolve: {
            journalVoucher: JournalVoucherResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'JournalVouchers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: JournalVoucherUpdateComponent,
        resolve: {
            journalVoucher: JournalVoucherResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'JournalVouchers'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const journalVoucherPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: JournalVoucherDeletePopupComponent,
        resolve: {
            journalVoucher: JournalVoucherResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'JournalVouchers'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
