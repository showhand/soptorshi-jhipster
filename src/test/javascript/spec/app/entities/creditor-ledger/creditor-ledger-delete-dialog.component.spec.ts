/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SoptorshiTestModule } from '../../../test.module';
import { CreditorLedgerDeleteDialogComponent } from 'app/entities/creditor-ledger/creditor-ledger-delete-dialog.component';
import { CreditorLedgerService } from 'app/entities/creditor-ledger/creditor-ledger.service';

describe('Component Tests', () => {
    describe('CreditorLedger Management Delete Component', () => {
        let comp: CreditorLedgerDeleteDialogComponent;
        let fixture: ComponentFixture<CreditorLedgerDeleteDialogComponent>;
        let service: CreditorLedgerService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [CreditorLedgerDeleteDialogComponent]
            })
                .overrideTemplate(CreditorLedgerDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CreditorLedgerDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CreditorLedgerService);
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
