/* tslint:disable max-line-length */
import { ComponentFixture, fakeAsync, inject, TestBed, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SoptorshiTestModule } from '../../../test.module';
import { CommercialBudgetDeleteDialogComponent } from 'app/entities/commercial-budget/commercial-budget-delete-dialog.component';
import { CommercialBudgetService } from 'app/entities/commercial-budget/commercial-budget.service';

describe('Component Tests', () => {
    describe('CommercialBudget Management Delete Component', () => {
        let comp: CommercialBudgetDeleteDialogComponent;
        let fixture: ComponentFixture<CommercialBudgetDeleteDialogComponent>;
        let service: CommercialBudgetService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [CommercialBudgetDeleteDialogComponent]
            })
                .overrideTemplate(CommercialBudgetDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CommercialBudgetDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CommercialBudgetService);
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
