/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SoptorshiTestModule } from '../../../test.module';
import { DebtorLedgerDeleteDialogComponent } from 'app/entities/debtor-ledger/debtor-ledger-delete-dialog.component';
import { DebtorLedgerService } from 'app/entities/debtor-ledger/debtor-ledger.service';

describe('Component Tests', () => {
    describe('DebtorLedger Management Delete Component', () => {
        let comp: DebtorLedgerDeleteDialogComponent;
        let fixture: ComponentFixture<DebtorLedgerDeleteDialogComponent>;
        let service: DebtorLedgerService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [DebtorLedgerDeleteDialogComponent]
            })
                .overrideTemplate(DebtorLedgerDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DebtorLedgerDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DebtorLedgerService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
