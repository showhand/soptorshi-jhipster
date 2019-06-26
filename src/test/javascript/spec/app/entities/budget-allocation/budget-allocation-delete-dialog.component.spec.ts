/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SoptorshiTestModule } from '../../../test.module';
import { BudgetAllocationDeleteDialogComponent } from 'app/entities/budget-allocation/budget-allocation-delete-dialog.component';
import { BudgetAllocationService } from 'app/entities/budget-allocation/budget-allocation.service';

describe('Component Tests', () => {
    describe('BudgetAllocation Management Delete Component', () => {
        let comp: BudgetAllocationDeleteDialogComponent;
        let fixture: ComponentFixture<BudgetAllocationDeleteDialogComponent>;
        let service: BudgetAllocationService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [BudgetAllocationDeleteDialogComponent]
            })
                .overrideTemplate(BudgetAllocationDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(BudgetAllocationDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BudgetAllocationService);
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
