/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SoptorshiTestModule } from '../../../test.module';
import { FineManagementDeleteDialogComponent } from 'app/entities/fine-management/fine-management-delete-dialog.component';
import { FineManagementService } from 'app/entities/fine-management/fine-management.service';

describe('Component Tests', () => {
    describe('FineManagement Management Delete Component', () => {
        let comp: FineManagementDeleteDialogComponent;
        let fixture: ComponentFixture<FineManagementDeleteDialogComponent>;
        let service: FineManagementService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [FineManagementDeleteDialogComponent]
            })
                .overrideTemplate(FineManagementDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(FineManagementDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FineManagementService);
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
